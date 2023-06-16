import BeefyClient from '../api/beefyClient';
import IdTable from '../components/idTable';
import Header from '../components/header';
import BindingClass from '../util/bindingClass';
import DataStore from '../util/DataStore';

class GetGoalById extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['mount', 'getGoalById'], this);
        this.dataStore = new DataStore();
        this.header = new Header(this.dataStore);
        this.idTable = new IdTable();
        console.log("GetGoalById constructor");
    }

    mount() {
        console.log("GetGoalById mounting...");
        const form = document.getElementById('view-goalId-form');
        if (form) {
            form.addEventListener('submit', this.getGoalById.bind(this));
        }

        this.header.addHeaderToPage();
        this.client = new BeefyClient();
    }


    async getGoalById(event) {
        event.preventDefault();
        console.log("from the getGoalByName method");

        const form = document.getElementById("view-goalId-form");
        const requestedGoalId = form.elements["requestedGoalId"].value;

        try {
            const retrievalRequest = await this.client.getGoalById(requestedGoalId);
            this.idTable.addTableToPage(requestedGoalId, retrievalRequest); // Pass the 'retrievalRequest' object directly
        } catch (error) {
            console.error(error);
            alert('Error retrieving item. See console for details.');
        }
    }



}

    const main = async () => {
        console.log('Main function called');
        const getGoalById = new GetGoalById();
        getGoalById.mount();
    };

    window.addEventListener('DOMContentLoaded', main);

