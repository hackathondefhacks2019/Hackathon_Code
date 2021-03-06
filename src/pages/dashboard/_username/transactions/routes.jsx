import React, { Component } from 'react';
import { Switch, Route } from 'react-router-dom';
import PropTypes from 'prop-types';
import TransactionCreate from '../../_username/transactions/create';
import AdminTransactions from '../transactions'

class AdminUsernameTransactionsRoute extends Component {
  static propTypes = {
    match: PropTypes.object.isRequired,
  };

   render() {
     return (
       <Switch>
         <Route
           exact
           path={this.props.match.url}
           render={() => <AdminTransactions />}
         />
         <Route
           path={`${this.props.match.url}/create`}
           render={() => <TransactionCreate />}
         />
         <Route
           exact
           path={`${this.props.match.url}/:id`}
           render={() => <div>View</div>}
         />
       </Switch>
      )
   }
}

export default AdminUsernameTransactionsRoute;
