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
        this.categoryTable = new CategoryTable();
        this.header = new Header(); // Modify this line
        console.log("getGoalByCategory constructor");
    }

    mount() {
        console.log("getGoalByCategory mounting...");
        const form = document.getElementById('view-goalcategory-form');
        if (form) {
            form.addEventListener('submit', this.getGoalByCategory.bind(this));
        }

        this.header.addHeaderToPage(); // Add this line
        this.client = new BeefyClient();
    }

    /**
     * This function retrieves data for a specific category of goals and displays it in a table on the
     * webpage.
     * @param event - The event parameter is an object that represents an event that occurred in the
     * browser, such as a button click or form submission. In this case, it is used to prevent the
     * default behavior of a form submission, which would cause the page to reload.
     */
    async getGoalByCategory(event) {
        event.preventDefault();
        console.log("from the getGoalByCategory method");

        const form = document.getElementById("view-goalcategory-form");
        const requestedCategory = form.elements["requestedCategory"].value;

        try {
            const retrievalRequest = await this.client.getGoalByCategory(requestedCategory);
            const data = retrievalRequest; 
            this.categoryTable.addTableToPage(requestedCategory, data); 
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
