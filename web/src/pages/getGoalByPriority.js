import BeefyClient from '../api/beefyClient';
import PriorityTable from '../components/priorityTable';
import Header from '../components/header';
import BindingClass from '../util/bindingClass';
import DataStore from '../util/DataStore';

class GetGoalByPriority extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['mount', 'getGoalByPriority'], this);
        this.dataStore = new DataStore();
        this.header = new Header(this.dataStore);
        this.priorityTable = new PriorityTable();
        console.log("getGoalByPriority constructor");
    }

    mount() {
        console.log("getGoalByPriority mounting...");
        const form = document.getElementById('view-goalpriority-form');
        if (form) {
            form.addEventListener('submit', this.getGoalByPriority.bind(this));
        }

        this.header.addHeaderToPage();
        this.client = new BeefyClient();
    }


    async getGoalByPriority(event) {
        event.preventDefault();
        console.log("from the getGoalByPriority method");

        const form = document.getElementById("view-goalpriority-form");
        const requestedPriority = form.elements["requestedPriority"].value;

        try {
            const retrievalRequest = await this.client.getGoalByPriority(requestedPriority);
            const data = retrievalRequest; // Assign the retrieved data to the 'data' variable
            this.priorityTable.addTableToPage(requestedPriority, data); // Pass the 'data' variable to the 'addTableToPage' method
        } catch (error) {
            console.error(error);
            alert('Error retrieving item. See console for details.');
        }
    }


}

const main = async () => {
    console.log('Main function called');
    const getGoalByPriority = new GetGoalByPriority();
    getGoalByPriority.mount();
};

window.addEventListener('DOMContentLoaded', main);
