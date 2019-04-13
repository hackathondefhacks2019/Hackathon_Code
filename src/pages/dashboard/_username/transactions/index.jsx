import React, { Component } from 'react'
import {
    Card,
    Content,
} from 'react-bulma-components'

import { MyContext} from '../../../../components/User/UserProvider';
import TransactionsTable from '../../../../components/Transaction/TransactionTable';

class AdminTransactions extends Component {
  state = {
    transactions: []
  };

  componentDidMount() {
    this.loadTransactions()
  }

  loadTransactions = async () => {
    const { userSession } = this.context.state.currentUser;
    const options = { decrypt: false };

    try {
      const result = await userSession.getFile('transactions.json', options);

      if (!result) {
        throw new Error('Transactions File does not exist')
      }

      return this.setState({ transactions: JSON.parse(result) })
    }
    catch (e) {
      console.log(e.message)
    }
  };

  render() {
    const { transactions } = this.state;

    return (
      <Card>
        <Card.Content>
          <Content>
            <TransactionsTable
              transactions={transactions}
            />
          </Content>
        </Card.Content>
      </Card>
    )
  }
}

export default AdminTransactions;

AdminTransactions.contextType = MyContext;
