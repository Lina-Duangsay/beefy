import BeefyClient from '../api/beefyClient';
import MusicPlaylistClient from '../api/beefyClient';
import Header from '../components/header';
import BindingClass from '../util/bindingClass';
import DataStore from '../util/DataStore';

/**
 * Logic needed for the create goal page of the website.
 */
class UserDashboard extends BindingClass {

constructor() {
    super();
    this.bindClassMethods(['mount', 'submit', 'redirectToViewGoal'], this);
    this.dataStore = new DataStore();
    this.dataStore.addChangeListener(this.redirectToViewGoal);
    this.header = new Header(this.dataStore);
}

/**
 * Add the header to the page and load the BeefyClient.
 */
mount() {
    document.getElementById('create').addEventListener('click', this.submit);

    this.header.addHeaderToPage();

    this.client = new BeefyClient();
}
}