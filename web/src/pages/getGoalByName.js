import BeefyClient from '../api/beefyClient';
import NameTable from '../components/nameTable';
import Header from '../components/header';
import BindingClass from '../util/bindingClass';
import DataStore from '../util/DataStore';

class GetGoalByName extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['mount', 'getGoalByName'], this);
        this.dataStore = new DataStore();
        this.header = new Header(this.dataStore);
        this.nameTable = new NameTable();
        console.log("getGoalByName constructor");
    }

    mount() {
        console.log("getGoalByName mounting...");
        const form = document.getElementById('view-goalname-form');
        if (form) {
            form.addEventListener('submit', this.getGoalByName.bind(this));
        }

        this.header.addHeaderToPage();
        this.client = new BeefyClient();
    }


    async getGoalByName(event) {
        event.preventDefault();
        console.log("from the getGoalByName method");

        const form = document.getElementById("view-goalname-form");
        const requestedName = form.elements["requestedName"].value;

        try {
            const retrievalRequest = await this.client.getGoalByName(requestedName);
            const data = retrievalRequest; // Assign the retrieved data to the 'data' variable
            this.nameTable.addTableToPage(requestedName, data); // Pass the 'data' variable to the 'addTableToPage' method
        } catch (error) {
            console.error(error);
            alert('Error retrieving item. See console for details.');
        }
    }


}

const main = async () => {
    console.log('Main function called');
    const getGoalByName = new GetGoalByName();
    getGoalByName.mount();
};

window.addEventListener('DOMContentLoaded', main);
