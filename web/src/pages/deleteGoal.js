import BeefyClient from '../api/beefyClient';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";
import Table from '../components/table';
import Header from '../components/header';

/**
 * Logic needed for the view table page of the website.
 */
class DeleteGoal extends BindingClass {
    constructor() {
        super();

        this.bindClassMethods(['mount', 'deleteGoal'], this);

        // Create a new datastore with an initial "empty" state.
        this.dataStore = new DataStore();
    
    }

    /**
         * Add the table to the page and load the MusicPlaylistClient.
         */
    mount() {
        console.log('deleteGoal.js mounting...');
        var deleteButton = document.getElementById("delete");
        deleteButton.addEventListener("click", (event) => this.deleteGoal(event));

        this.client = new BeefyClient();
}



    /**
 * Method to run when the update goal update button is pressed. Call the BeefyClient to create the
 * playlist.
 */
    async delete(evt) {
        evt.preventDefault();

        const errorMessageDisplay = document.getElementById('error-message');
        errorMessageDisplay.innerText = ``;
        errorMessageDisplay.classList.add('hidden');

        const deleteButton = document.getElementById('delete');
        const origButtonText = deleteButton.innerText;
        deleteButton.innerText = 'Loading...';

        const goalId = document.getElementById('goalId').value;

        const goal = await this.client.deleteGoal(goalId, (error) => {
            deleteButton.innerText = origButtonText;
            errorMessageDisplay.innerText = `Error: ${error.message}`;
            errorMessageDisplay.classList.remove('hidden');
        });
        this.dataStore.set('goal', goal);
    }


    /**
         * Method to run when the update button is pressed. Call the BeefyServiceCleint to update the inventory.
         */
    async deleteGoal(event) {
        event.preventDefault();
        console.log("hello from deleteGoal method")
        const form = document.getElementById("delete-goal-form");
        const goalId = form.elements["goalId"].value;

        try {
            const updateRequest = await this.client.deleteGoal(goalId);
            alert('Item deleted successfully!');
        } catch (error) {
            console.error(error);
            alert('Error updating item. See console for details.');
        }
    }

}
const main = async () => {
    const deleteGoal = new DeleteGoal();
    deleteGoal.mount();
};

window.addEventListener('DOMContentLoaded', main);

