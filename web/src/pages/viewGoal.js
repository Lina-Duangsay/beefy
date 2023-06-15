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
        this.bindClassMethods(['clientLoaded', 'mount', 'addPlaylistToPage', 'addSongsToPage', 'addSong'], this);
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.addPlaylistToPage);
        this.dataStore.addChangeListener(this.addSongsToPage);
        this.header = new Header(this.dataStore);
        console.log("viewgoal constructor");
    }

    /**
     * Once the client is loaded, get the playlist metadata and song list.
     */
    async clientLoaded() {
        const urlParams = new URLSearchParams(window.location.search);
        const goalId = urlParams.get('goal-id');
        // document.getElementById('playlist-name').innerText = "Loading Goal ...";
        const retrievedGoal = await this.client.getGoal(goalId);
        this.dataStore.set('goal', goal);
        // document.getElementById('songs').innerText = "(loading songs...)";
        // const songs = await this.client.getPlaylistSongs(playlistId);
        // this.dataStore.set('songs', songs);
    }

    /**
     * Add the header to the page and load the MusicPlaylistClient.
     */
    mount() {
        // document.getElementById('add-song').addEventListener('click', this.addSong);

        this.header.addHeaderToPage();

        this.client = new BeefyClient();
        this.clientLoaded();
    }

    /**
     * When the playlist is updated in the datastore, update the playlist metadata on the page.
     */
    addGoalToPage() {
        const goal = this.dataStore.get('goal');
        if (goal == null) {
            return;
        }

        document.getElementById('goal-id').innerText = goal.goalId;
        document.getElementById('goal-name').innerText = goal.goalName;
        document.getElementById('goal-amount').innerText = goal.goalAmount;
        document.getElementById('goal-category').innerText = goal.goalCategory;
        document.getElementById('goal-priority').innerText = goal.goalPriority;
        document.getElementById('goal-completionStatus').innerText = goal.completionStatus;

        let goalHtml = '';
        let tag;
        for (tag of playlist.tags) {
            tagHtml += '<div class="tag">' + tag + '</div>';
        }
        document.getElementById('tags').innerHTML = tagHtml;
    }

    /**
     * When the songs are updated in the datastore, update the list of songs on the page.
     */
    addSongsToPage() {
        const songs = this.dataStore.get('songs')

        if (songs == null) {
            return;
        }

        let songHtml = '';
        let song;
        for (song of songs) {
            songHtml += `
                <li class="song">
                    <span class="title">${song.title}</span>
                    <span class="album">${song.album}</span>
                </li>
            `;
        }
        document.getElementById('songs').innerHTML = songHtml;
    }

    /**
     * Method to run when the add song playlist submit button is pressed. Call the MusicPlaylistService to add a song to the
     * playlist.
     */
    async addSong() {

        const errorMessageDisplay = document.getElementById('error-message');
        errorMessageDisplay.innerText = ``;
        errorMessageDisplay.classList.add('hidden');

        const playlist = this.dataStore.get('playlist');
        if (playlist == null) {
            return;
        }

        document.getElementById('add-song').innerText = 'Adding...';
        const asin = document.getElementById('album-asin').value;
        const trackNumber = document.getElementById('track-number').value;
        const playlistId = playlist.id;

        const songList = await this.client.addSongToPlaylist(playlistId, asin, trackNumber, (error) => {
            errorMessageDisplay.innerText = `Error: ${error.message}`;
            errorMessageDisplay.classList.remove('hidden');
        });

        this.dataStore.set('songs', songList);

        document.getElementById('add-song').innerText = 'Add Song';
        document.getElementById("add-song-form").reset();
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