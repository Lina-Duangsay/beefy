import BeefyClient from '../api/beefyClient';
import MusicPlaylistClient from '../api/beefyClient';
import Header from '../components/header';
import BindingClass from '../util/bindingClass';
import DataStore from '../util/DataStore';

/**
 * Logic needed for the create goal page of the website.
 */
class CreateGoal extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['mount', 'submit', 'redirectToViewGoal'], this);
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.redirectToViewGoal);
        this.header = new Header(this.dataStore);
    }

    /**
     * Add the header to the page and load the BeefyClient.
     */
    mount() {
        document.getElementById('create').addEventListener('click', this.submit);

        this.header.addHeaderToPage();

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
        const prioirty = document.getElementById('priority').value;

        const goal = await this.client.createGoal(name, category, goalAmount, description, prioirty, (error) => {
            createButton.innerText = origButtonText;
            errorMessageDisplay.innerText = `Error: ${error.message}`;
            errorMessageDisplay.classList.remove('hidden');
        });
        this.dataStore.set('goal', goal);
    }

    /**
     * When the goal is updated in the datastore, redirect to the view goal page.
     */
    redirectToViewGoal() {
        const goal = this.dataStore.get('goal');
        if (goal != null) {
            window.location.href = `/goal/{goal.id}`;
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
