import BeefyClient from '../api/beefyClient';
import Header from '../components/header';
import BindingClass from '../util/bindingClass';
import DataStore from '../util/DataStore';

/**
 * Logic needed for the create goal page of the website.
 */
class UserDashboard extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['mount',], this);
        this.header = new Header();
    }

    /**
     * Add the header to the page and load the BeefyClient.
     */
    mount() {
        console.log("made to mount");
        this.client = new BeefyClient();
        this.header.addHeaderToPage();
    }

}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const userDashboard = new UserDashboard();
    userDashboard.mount();
};

window.addEventListener('DOMContentLoaded', main);
