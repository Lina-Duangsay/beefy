import BeefyClient from '../api/beefyClient';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";
import CompletionTable from '../components/completionTable';
import Header from '../components/header';

/**
 * Logic needed for the view table page of the website.
 */
class UpdateGoalStatus extends BindingClass {
    constructor() {
        super();

        this.bindClassMethods(['mount', 'updateGoalStatus', 'update'], this);

        this.dataStore = new DataStore();
        this.client = new BeefyClient();
        this.completionTable = new CompletionTable(this.dataStore);
        this.header = new Header(this.dataStore);
    }

    /**
         * Add the table to the page and load the BeefyClient.
         */
    mount() {
        console.log('updateGoalStatus.js mounting...');
        this.completionTable.addTableToPage();
        const updateButton = document.getElementById("update");
        updateButton.addEventListener("click", this.updateGoalStatus);
    }



    /**
 * Method to run when the update goal update button is pressed. Call the BeefyClient to create the
 * playlist.
 */
    async update(evt) {
        evt.preventDefault();

        const errorMessageDisplay = document.getElementById('error-message');
        errorMessageDisplay.innerText = '';
        errorMessageDisplay.classList.add('hidden');

        const updateButton = document.getElementById('update');
        const origButtonText = updateButton.innerText;
        updateButton.innerText = 'Loading...';

        const goalId = document.getElementById('goalId').value;
        const completionStatus = document.getElementById('completionStatus').value;

        const goal = await this.client.updateGoalStatus(goalId, completionStatus, (error) => {
            updateButton.innerText = origButtonText;
            errorMessageDisplay.innerText = `Error: ${error.message}`;
            errorMessageDisplay.classList.remove('hidden');
        });
        this.dataStore.set('goal', goal);
    }



    /**
         * Method to run when the update button is pressed. Call the BeefyServiceCleint to update the inventory.
         */
    async updateGoalStatus(event) {
        event.preventDefault();
        console.log("hello from updateGoalStatus method")
        const form = document.getElementById("update-goal-status-form");
        const goalId = form.elements["goalId"].value;
        const completionStatus = form.elements["completionStatus"].value;

        try {
            const updateRequest = await this.client.updateGoalStatus(goalId, completionStatus);
            alert('Goal updated successfully! Congrats on meeting your goal!');
            window.location.reload();
        } catch (error) {
            console.error(error);
            alert('Error updating item. See console for details.');
        }
    }

}
const main = async () => {
    const updateGoalStatus = new UpdateGoalStatus();
    updateGoalStatus.mount();
};

window.addEventListener('DOMContentLoaded', main);

