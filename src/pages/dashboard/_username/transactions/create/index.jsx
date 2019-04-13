import React, { Component } from 'react';
import TransactionForm from '../../../../../components/Transaction/TransactionForm';
import { MyContext } from '../../../../../components/User/UserProvider';

class TransactionCreate extends Component {
  render() {
    const { userSession, username } = this.context.state.currentUser;

    return (
      <TransactionForm
        username={username}
        userSession={userSession}
      />
    )
  }
}

TransactionCreate.contextType = MyContext;

export default TransactionCreate;
