import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { Button } from 'react-bulma-components';

import Loader from '../Loader';

export default class Login extends Component {
  state = {
    loading: false
  };

  static propTypes = {
    userSession: PropTypes.object.isRequired,
  };

  handleSignIn = (e) => {
    const { userSession } = this.props;
    e.preventDefault();
    userSession.redirectToSignIn();
    this.setState({ loading: true })
  };

  render() {
    const { loading } = this.state;

    return (
      <div className="wrapper">
      {
        loading ? <Loader /> :
          <Button color="primary is-large" onClick={this.handleSignIn}>
            Sign in with Blockstack
          </Button>
        }
      </div>
    )
  }
}
