import BeefyClient from '../api/beefyClient';
import BindingClass from '../util/bindingClass';
import DataStore from '../util/DataStore';
import Header from '../components/header';
import Table from '../components/table';


/**
 * Logic needed for the view playlist page of the website.
 */
class GetGoalById extends BindingClass {
    constructor() {
        super();

        this.bindClassMethods(['mount', 'getGoalById'], this);

        // Create a enw datastore with an initial "empty" state.
        this.dataStore = new DataStore(EMPTY_DATASTORE_STATE);
        this.header = new Header(this.dataStore);

        console.log("getGoalById constructor");
    }

    /**
     * Add the header to the page and load the MusicPlaylistClient.
     */
    mount() {
        console.log("getGoalById mounting...");

        document.getElementById('view-goalId-form').addEventListener('view', this.search);
        document.getElementById('view-goal').addEventListener('click', this.search);


        this.header.addHeaderToPage();

        this.client = new BeefyClient();
    }

    async getGoalById(event) {
        event.preventDefault();
        console.log("from the getGoalById method")

        const form = document.getElementById("view-goalId-form");
        const goalId = form.elements["goalId"].value;

        try {
            const retrievalRequest = await this.client.getGoalById(goalId);
        } catch (error) {
            console.error(error);
            alert('Error retrieving item. See console for details.');
        }


    }

}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const getGoalById = new GetGoalById();
    getGoalById.mount();
};

window.addEventListener('DOMContentLoaded', main);