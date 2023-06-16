import BeefyClient from '../api/beefyClient';
import CategoryTable from '../components/categoryTable';
import Header from '../components/header';
import BindingClass from '../util/bindingClass';
import DataStore from '../util/DataStore';

class GetGoalByCategory extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['mount', 'getGoalByCategory'], this);
        this.dataStore = new DataStore();
        // this.header = new Header(this.dataStore);
        this.categoryTable = new CategoryTable();
        console.log("getGoalByCategory constructor");
    }

    mount() {
        console.log("getGoalByCategory mounting...");
        const form = document.getElementById('view-goalcategory-form');
        if (form) {
            form.addEventListener('submit', this.getGoalByCategory.bind(this));
        }

        this.header.addHeaderToPage();
        this.client = new BeefyClient();
    }


    async getGoalByCategory(event) {
        event.preventDefault();
        console.log("from the getGoalByCategory method");

        const form = document.getElementById("view-goalcategory-form");
        const requestedCategory = form.elements["requestedCategory"].value;

        try {
            const retrievalRequest = await this.client.getGoalByCategory(requestedCategory);
            const data = retrievalRequest; // Assign the retrieved data to the 'data' variable
            this.categoryTable.addTableToPage(requestedCategory, data); // Pass the 'data' variable to the 'addTableToPage' method
        } catch (error) {
            console.error(error);
            alert('Error retrieving item. See console for details.');
        }
    }


}

const main = async () => {
    console.log('Main function called');
    const getGoalByCategory = new GetGoalByCategory();
    getGoalByCategory.mount();
};

window.addEventListener('DOMContentLoaded', main);
