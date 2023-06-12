import BeefyClient from '../api/beefyClient';
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";

/**
 * Logic needed for the view playlist page of the website.
 */
class ViewGoal extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['clientLoaded', 'mount', 'addGoalToPage'], this);
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.addGoalToPage);

        this.header = new Header(this.dataStore);
        console.log("viewgoal constructor");
    }

    /**
     * Once the client is loaded, get the goal metadata.
     */
    async clientLoaded() {
        const urlParams = new URLSearchParams(window.location.search);
        const goalId = urlParams.get('goal-id');
        document.getElementById('goal-id').innerText = "Loading Goal ...";
        const goal = await this.client.getGoal(goalId);
        this.dataStore.set('goal', goal);
    }


    /**
     * Add the header to the page and load the BeefyClient.
     */
    mount() {

        document.getElementById('submit').addEventListener('click', this.submit);
        this.header.addHeaderToPage();

        this.client = new BeefyClient();
        this.clientLoaded();
    }

    /**
     * When the goal is updated in the datastore, update the goal metadata on the page.
     */
    addGoalToPage() {
        const goal = this.dataStore.get('goal');
        if (goal == null) {
            return;
        }

        document.getElementById('goal-id').innerText = goal.name;
        document.getElementById('user-id').innerText = goal.userId;
    }

    async submit(evt) {
        evt.preventDefault();

        const submission = document.createElement("submit");
        submission.type = "button";
        submission.className = "button-bordered";
        submission.id = "submit";
        submission.innerText = "submit";

        submission.addEventListener("click", this.retrieveGoal);

        const forming = document.querySelector

    }

}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const viewGoal = new ViewGoal();
    viewGoal.mount();
};

window.addEventListener('DOMContentLoaded', main);
