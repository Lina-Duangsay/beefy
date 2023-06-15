import BeefyClient from '../api/beefyClient';
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";
import Table from '../components/table';

const SEARCH_CRITERIA_KEY = 'search-criteria';
const SEARCH_RESULTS_KEY = 'search-results';
const EMPTY_DATASTORE_STATE = {
    [SEARCH_CRITERIA_KEY]: '',
    [SEARCH_RESULTS_KEY]: [],
};

/**
 * Logic needed for the view table page of the website.
 */
class ViewAllGoals extends BindingClass {
    constructor() {
        super();

        this.bindClassMethods(['mount'], this);

        this.dataStore = new DataStore(EMPTY_DATASTORE_STATE);
        this.table = new Table(this.dataStore);
        this.dataStore.addChangeListener(this.displaySearchResults);
        this.header = new Header(this.dataStore);
        this.client = new BeefyClient();
    }

    /**
     * Add the table to the page and load the BeefyClient.
     */
    async mount() {
        console.log('ViewAllGoals.js mounting...');
        this.table.addTableToPage();
        this.header.addHeaderToPage();

        const token = await this.getTokenFromLocalStorage();
    }

    async getTokenFromLocalStorage() {
        return localStorage.getItem('token');
    }
}

const main = async () => {
    const viewAllGoals = new ViewAllGoals();
    viewAllGoals.mount();
};

window.addEventListener('DOMContentLoaded', main);
