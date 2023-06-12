import BeefyClient from '../api/beefyClient';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";
import Table from '../components/table';
import Header from '../components/header';

/**
 * Logic needed for the view table page of the website.
 */
class UpdateGoalPriority extends BindingClass {
    constructor() {
        super();

        this.bindClassMethods(['mount','updateGoalPriority'], this);

        // Create a new datastore with an initial "empty" state.
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.displaySearchResults);
        this.client = new BeefyClient();
    }

    /**
         * Add the table to the page and load the MusicPlaylistClient.
         */
    mount() {
        console.log('UpdateGoalPriority.js mounting...');
        var updateButton = document.getElementById("update");
        updateButton.addEventListener("click", this.updateGoalPriority);
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
        const priority = document.getElementById('priority').value;

        const goal = await this.client.updateGoalAmount(goalId, priority, (error) => {
            updateButton.innerText = origButtonText;
            errorMessageDisplay.innerText = `Error: ${error.message}`;
            errorMessageDisplay.classList.remove('hidden');
        });
        this.dataStore.set('goal', goal);
    }

    /**
 * Method to run when the create goal update button is pressed. Call the BeefyClient to create the
 * playlist.
 */
    async update(evt) {
        evt.preventDefault();

        const errorMessageDisplay = document.getElementById('error-message');
        errorMessageDisplay.innerText = ``;
        errorMessageDisplay.classList.add('hidden');

        const updateButton = document.getElementById('update');
        const origButtonText = update.innerText;
        update.innerText = 'Loading...';

        const goalId = document.getElementById('goalId').value;
        const priority = document.getElementById('priority').value;

        const goal = await this.client.createGoal(goalId, priority, (error) => {
            createButton.innerText = origButtonText;
            errorMessageDisplay.innerText = `Error: ${error.message}`;
            errorMessageDisplay.classList.remove('hidden');
        });
        this.dataStore.set('goal', goal);
    }



    /**
         * Method to run when the update button is pressed. Call the BeefyServiceCleint to update the inventory.
         */
    async updateGoalPriority(event) {
        event.preventDefault();
        console.log("hello from updateGoalPriority method")
        const form = document.getElementById("update-goal-priority-form");
        const goalId = form.elements["goalId"].value;
        const priority = form.elements["priority"].value;

        try {
            const updateRequest = await this.client.updateGoalPriority(goalId, priority);
            alert('Item updated successfully!');
        } catch (error) {
            console.error(error);
            alert('Error updating item. See console for details.');
        }
    }
}
    const main = async () => {
        const updateGoalPriority = new UpdateGoalPriority();
        updateGoalPriority.mount();
    };

window.addEventListener('DOMContentLoaded', main);