import React, { Component } from 'react';
import PropTypes from 'prop-types';
import _ from 'lodash';
import { Table } from 'react-bulma-components';
import { withRouter } from 'react-router-dom';

class TransactionsTable extends Component {
  static propTypes = {
    userSession: PropTypes.object.isRequired,
    username: PropTypes.string.isRequired,
    transactions: PropTypes.array.isRequired
  };

  render() {
    const { transactions } = this.props;

    return (
      <Table>
        <thead>
          <tr>
            <th>Amount</th>
            <th>Date</th>
          </tr>
          </thead>
          <tbody>
          {
            _.map(transactions, (transaction) => {
               return (
                 <tr>
                   <td>{transaction.amount}</td>
                   <td>{transaction.date}</td>
                 </tr>
              )
            })
          }
          </tbody>
      </Table>
      )
    }
}

export default withRouter(TransactionsTable)
