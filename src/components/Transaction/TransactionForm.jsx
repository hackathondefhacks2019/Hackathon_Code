import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { withRouter } from 'react-router-dom';

import {
    Button,
    Card,
    Content,
} from 'react-bulma-components';

class TransactionForm extends Component {
  state = {
    amount: '',
    date: '',
    transactions: [],
  };

  static propTypes = {
    userSession: PropTypes.object.isRequired,
      username: PropTypes.string.isRequired
    };

    componentDidMount() {
        this.loadTransactions()
    }

    loadTransactions = async () => {
        const { userSession } = this.props;
        const options = { decrypt: false };

        const result = await userSession.getFile('transactions.json', options);

        if (result) {
            return this.setState({ transactions: JSON.parse(result) })
        }

        return null
    };

    createTransaction = async () => {
      const options = { encrypt: false };
      const { amount, date, transactions } = this.state;
      const { history, userSession, username } = this.props;

      const params = {
        amount,
        date
      };

      try {
        await userSession.putFile('transactions.json', JSON.stringify([...transactions, params]), options);
        this.setState({
          title: '',
          description: ''
        }, () => history.push(`/dashboard/${username}/transactions`))
      }
      catch (e) {
        console.log(e)
      }
    };

    onChange = (e) => {
      this.setState({
        [e.target.name]: e.target.value
      })
    };

    onSubmit = (e) => {
      e.preventDefault();
      this.createTransaction();
    };

    render() {
      return (
        <Card>
          <Card.Content>
            <Content>
              <form onSubmit={this.onSubmit}>
                <label className="label">Amount</label>
                <div className="control">
                  <input className="input" type="text" name="amount" placeholder="Amount of the Transaction" onChange={this.onChange} value={this.state.amount} />
                </div>
                <label className="label">Date</label>
                <div className="control">
                  <input className="input" type="text" name="date" placeholder="Date of the Transaction" onChange={this.onChange} value={this.state.date} />
                </div>
                <Button color="link" type="submit">
                  Submit
                </Button>
              </form>
            </Content>
          </Card.Content>
        </Card>
      )
    }
}

export default withRouter(TransactionForm)
