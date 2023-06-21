import BeefyClient from '../api/beefyClient';
import Header from '../components/header';
import BindingClass from '../util/bindingClass';
import DataStore from '../util/DataStore';

/**
 * Logic needed for the create goal page of the website.
 */
class CreateGoal extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['mount', 'submit'], this);
        this.dataStore = new DataStore();
        this.header = new Header(this.dataStore);
    }

    /**
     * Add the header to the page and load the BeefyClient.
     */
    mount() {
        this.header.addHeaderToPage();
        document.getElementById('create').addEventListener('click', this.submit);
        this.client = new BeefyClient();
    }

    /**
     * Method to run when the create goal submit button is pressed. Call the BeefyClient to create the
     * playlist.
     */
    async submit(evt) {
        evt.preventDefault();

        const errorMessageDisplay = document.getElementById('error-message');
        errorMessageDisplay.innerText = ``;
        errorMessageDisplay.classList.add('hidden');

        const createButton = document.getElementById('create');
        const origButtonText = createButton.innerText;
        createButton.innerText = 'Loading...';

        const name = document.getElementById('goal-name').value;
        const goalAmount = document.getElementById('goalAmount').value;
        const category = document.getElementById('category').value;
        const description = document.getElementById('description').value;
        const priority = document.getElementById('priority').value;

        try {
            const goal = await this.client.createGoal(name, category, goalAmount, description, priority);
            this.dataStore.set('goal', goal);
            alert('Goal created successfully! Visit your goals to view. ');
            window.location.reload();

        } catch (error) {
            createButton.innerText = origButtonText;
            errorMessageDisplay.innerText = `Error: ${error.message}`;
            errorMessageDisplay.classList.remove('hidden');
        }
    }

}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const createGoal = new CreateGoal();
    createGoal.mount();
};

window.addEventListener('DOMContentLoaded', main);
