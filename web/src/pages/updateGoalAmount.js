import BeefyClient from '../api/beefyClient';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";
import Table from '../components/table';
import Header from '../components/header';

/**
 * Logic needed for the view table page of the website.
 */
class UpdateGoalAmount extends BindingClass {
    constructor() {
        super();

        this.bindClassMethods(['mount', 'updateGoalAmount', 'update'], this);

        this.dataStore = new DataStore();
        this.client = new BeefyClient();
        this.table = new Table(this.dataStore);
        this.header = new Header(this.dataStore);
    }

    /**
         * Add the table to the page and load the BeefyServiceClient.
         */
    mount() {
        console.log('UpdateGoalAmount.js mounting...');
        this.table.addTableToPage();
        var updateButton = document.getElementById("update");
        updateButton.addEventListener("click", this.updateGoalAmount);

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
        const amount = document.getElementById('amount').value;

        const goal = await this.client.updateGoalAmount(goalId, amount, (error) => {
            updateButton.innerText = origButtonText;
            errorMessageDisplay.innerText = `Error: ${error.message}`;
            errorMessageDisplay.classList.remove('hidden');
            
        });
        this.dataStore.set('goal', goal);
    }


    /**
         * Method to run when the update button is pressed. Call the BeefyClient to update the inventory.
         */
    async updateGoalAmount(event) {
        event.preventDefault();
        console.log("hello from updateGoalAmount method")
        const form = document.getElementById("update-goal-amount-form");
        const goalId = form.elements["goalId"].value;
        const amount = form.elements["amount"].value;

        try {
            const updateRequest = await this.client.updateGoalAmount(goalId, amount);
            alert('Item updated successfully!');
            window.location.reload();
        } catch (error) {
            console.error(error);
            alert('Error updating item. See console for details.');
        }
    }

}
const main = async () => {
    const updateGoalAmount = new UpdateGoalAmount();
    updateGoalAmount.mount();
};

window.addEventListener('DOMContentLoaded', main);

