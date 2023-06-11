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
class AllGoals extends BindingClass {
    constructor() {
        super();

        this.bindClassMethods(['mount'], this);

        // Create a enw datastore with an initial "empty" state.
        this.dataStore = new DataStore(EMPTY_DATASTORE_STATE);
        this.table = new Table(this.dataStore);
        this.dataStore.addChangeListener(this.displaySearchResults);
    }

    /**
     * Add the table to the page and load the MusicPlaylistClient.
     */
    mount() {
        console.log('AllGoals.js mounting...');
        this.table.addTableToPage();
        this.client = new BeefyClient();
        this.header.addHeaderToPage();
    }
}

const main = async () => {
    const inventory = new AllGoals();
    AllGoals.mount();
};

window.addEventListener('DOMContentLoaded', main);