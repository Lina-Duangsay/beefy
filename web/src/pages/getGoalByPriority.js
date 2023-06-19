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


    /**
     * This function retrieves a goal by priority and adds it to a table on the webpage.
     * @param event - The event parameter is an object that represents an event that occurred in the
     * browser, such as a button click or form submission. In this case, it is used to prevent the
     * default behavior of a form submission, which would cause the page to reload.
     */
    async getGoalByPriority(event) {
        event.preventDefault();
        console.log("from the getGoalByPriority method");

        const form = document.getElementById("view-goalpriority-form");
        const requestedPriority = form.elements["requestedPriority"].value;

        try {
            const retrievalRequest = await this.client.getGoalByPriority(requestedPriority);
            const data = retrievalRequest;
            this.priorityTable.addTableToPage(requestedPriority, data); 
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
