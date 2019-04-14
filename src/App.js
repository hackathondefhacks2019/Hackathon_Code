import React, {Component} from 'react';
import './stylesheets/main.scss';
import {appConfig} from './constants';
import {UserSession} from 'blockstack';
import {Button} from 'react-bulma-components';

class App extends Component {
    state = {
        userSession: new UserSession({appConfig})
    };

    componentDidMount = async () => {
        const {userSession} = this.state;
        if (!userSession.isUserSignedIn() && userSession.isSignInPending()) {
            const userData = await userSession.handlePendingSignIn();
            if (!userData.username) {
                throw new Error('This app requires a username');
            }
            window.location = '/'
        }
    };

    handleSignIn = () => {
        const {userSession} = this.state;
        userSession.redirectToSignIn();
    };

    handleSignOut = () => {
        const {userSession} = this.state;
        userSession.signUserOut();
        window.location = '/';
    };

    render() {
        console.log(this.state.userSession);
        const {userSession} = this.state;
        return (
            <div className="App">
                <div className="wrapper">
                {  
                    userSession.isUserSignedIn() ?
                        <Button color="primary is-large" onClick={this.handleSignOut}>
                            Sign out
                        </Button> :
                        <Button color="primary is-large" onClick={this.handleSignIn}>
                            Sign in
                        </Button>
                }
                </div>
            </div>
        );
    }
}

export default App;
