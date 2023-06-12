import BeefyClient from '../api/beefyClient';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";
import Table from '../components/table';
import Header from '../components/header';

/**
 * Logic needed for the view table page of the website.
 */
class UpdateGoalDescription extends BindingClass {
    constructor() {
        super();

        this.bindClassMethods(['mount', 'updateGoalDescription'], this);

        // Create a new datastore with an initial "empty" state.
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.displaySearchResults);
        this.client = new BeefyClient();
    }

    /**
         * Add the table to the page and load the MusicPlaylistClient.
         */
    mount() {
        console.log('UpdateGoalDescription.js mounting...');
        var updateButton = document.getElementById("update");
        updateButton.addEventListener("click", this.updateGoalDescription);
        this.header.addHeaderToPage();

    }

    /**
* Method to run when the update goal update button is pressed. Call the BeefyClient to create the
* playlist.
*/
    async update(evt) {
        evt.preventDefault();

        const errorMessageDisplay = document.getElementById('error-message');
        errorMessageDisplay.innerText = ``;
        errorMessageDisplay.classList.add('hidden');

        const updateButton = document.getElementById('update');
        const origButtonText = updateButton.innerText;
        updateButton.innerText = 'Loading...';

        const goalId = document.getElementById('goalId').value;
        const description = document.getElementById('description').value;

        const goal = await this.client.updateGoalAmount(goalId, description, (error) => {
            updateButton.innerText = origButtonText;
            errorMessageDisplay.innerText = `Error: ${error.message}`;
            errorMessageDisplay.classList.remove('hidden');
        });
        this.dataStore.set('goal', goal);
    }

    /**
         * Method to run when the update button is pressed. Call the BeefyServiceCleint to update the inventory.
         */
    async updateGoalDescription(event) {
        event.preventDefault();
        console.log("hello from updateGoalDescription method")
        const form = document.getElementById("update-goal-description-form");
        const goalId = form.elements["goalId"].value;
        const description = form.elements["description"].value;

        try {
            const updateRequest = await this.client.updateGoalDescription(goalId, description);
            alert('Item updated successfully!');
        } catch (error) {
            console.error(error);
            alert('Error updating item. See console for details.');
        }
    }
}

const main = async () => {
    const updateGoalDescription = new UpdateGoalDescription();
    updateGoalDescription.mount();
};

window.addEventListener('DOMContentLoaded', main);

