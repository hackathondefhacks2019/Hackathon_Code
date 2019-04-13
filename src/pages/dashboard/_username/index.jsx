import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { Heading, Button, Content } from 'react-bulma-components';
import { withRouter } from 'react-router-dom';

class AdminUsername extends Component {
  static propTypes = {
    username: PropTypes.string.isRequired,
  };

  navigateToCreate = () => {
    const { history, username } = this.props;
    history.push(`/dashboard/${username}/transactions/create`);
  };

  render() {
    const { username } = this.props;

    return (
      <div>
        <Content>
          <Heading renderAs="h2">Hello {username}</Heading>
            <Button
                color="primary"
                onClick={this.navigateToCreate}
            >
            Create Transaction
            </Button>
        </Content>
      </div>
    )
  }
}

export default withRouter(AdminUsername)
