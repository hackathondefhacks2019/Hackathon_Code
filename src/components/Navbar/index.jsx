import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { Navbar } from 'react-bulma-components';

export default class NavbarComp extends Component {
  state = {
    open: false
  };

  static propTypes = {
    userSession: PropTypes.object.isRequired
  };

  handleSignOut = () => {
    const { userSession } = this.props;
    userSession.signUserOut();
    window.location = '/'
  };

  toggleNavBar = () => {
    this.setState({ open: !this.state.open })
  };

  render() {
    const { userSession } = this.props;
    const { open } = this.state;
    const isSignedIn = userSession.isUserSignedIn();

    return (
      <Navbar
        color="info"
        fixed="top"
        active={open}
      >
        <Navbar.Brand>
          <Navbar.Item>
            <p>Blockstack credit scores</p>
          </Navbar.Item>

          <Navbar.Burger onClick={this.toggleNavBar} />
        </Navbar.Brand>

        <Navbar.Menu>
          <Navbar.Container position="end">
            {
              isSignedIn &&
              <React.Fragment>
                <Navbar.Item onClick={this.handleSignOut}>
                  Sign Out
                </Navbar.Item>
              </React.Fragment>
            }
          </Navbar.Container>
        </Navbar.Menu>
      </Navbar>
    )
  }
}
