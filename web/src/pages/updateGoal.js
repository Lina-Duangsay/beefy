import BeefyClient from '../api/beefyClient';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";
import Table from '../components/table';
import Header from '../components/header';

/**
 * Logic needed for the view table page of the website.
 */
class UpdateGoal extends BindingClass {
    constructor() {
        super();

        this.bindClassMethods(['mount', 'updateGoalAmount', 'updateGoalDescription'], this);

        // Create a new datastore with an initial "empty" state.
        this.dataStore = new DataStore();
        this.table = new Table(this.dataStore);
        this.dataStore.addChangeListener(this.displaySearchResults);
        this.client = new BeefyClient();
    }

    /**
         * Add the table to the page and load the MusicPlaylistClient.
         */
    mount() {
        console.log('UpdateGoal.js mounting...');
        this.table.addTableToPage();
        var updateButton = document.getElementById("update");
        updateButton.addEventListener("click", this.updateInventory);
        this.header.addHeaderToPage();

    }

    /**
         * Method to run when the update button is pressed. Call the BeefyServiceCleint to update the inventory.
         */
    async updateGoalAmount(event) {
        event.preventDefault();
        console.log("hello from updateGoalAmount method")
        const form = document.getElementById("update-goal-amount-form");
        const goalId = form.elements["goalId"].value;
        const goalAmount = form.elements["goalAmount"].value;

        try {
            const updateRequest = await this.client.updateGoalAmount(goalId, goalAmount);
            alert('Item updated successfully!');
        } catch (error) {
            console.error(error);
            alert('Error updating item. See console for details.');
        }
    }


    /**
         * Method to run when the update button is pressed. Call the BeefyServiceCleint to update the inventory.
         */
    async updateGoalDescription(event) {
        event.preventDefault();
        console.log("hello from updateGoalDescription method")
        const form = document.getElementById("update-goal-description-form");
        const goalId = form.elements["goalId"].value;
        const goalDescription = form.elements["goalDescription"].value;

        try {
            const updateRequest = await this.client.updateGoalDescription(goalId, goalDescription);
            alert('Item updated successfully!');
        } catch (error) {
            console.error(error);
            alert('Error updating item. See console for details.');
        }
    }

    /**
         * Method to run when the update button is pressed. Call the BeefyServiceCleint to update the inventory.
         */
    async updateGoalPriority(event) {
        event.preventDefault();
        console.log("hello from updateGoalPriority method")
        const form = document.getElementById("update-goal-priority-form");
        const goalId = form.elements["goalId"].value;
        const goalPriority = form.elements["goalPriority"].value;

        try {
            const updateRequest = await this.client.updateGoalPriority(goalId, goalPriority);
            alert('Item updated successfully!');
        } catch (error) {
            console.error(error);
            alert('Error updating item. See console for details.');
        }
    }
}

const main = async () => {
    const updateGoal = new UpdateGoal();
    updateGoal.mount();
};

window.addEventListener('DOMContentLoaded', main);

