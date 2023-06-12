import axios from "axios";
import BindingClass from "../util/bindingClass";
import Authenticator from "./authenticator";

/**
 * Client to call the BeefyTheBudgetBuddyService.
 *
  */
export default class BeefyClient extends BindingClass {

    constructor(props = {}) {
        super();

        const methodsToBind = ['clientLoaded', 'getIdentity', 'login', 'logout', 'viewAllGoals', 'createGoal'];
        this.bindClassMethods(methodsToBind, this);

        this.authenticator = new Authenticator();;
        this.props = props;

        axios.defaults.baseURL = process.env.API_BASE_URL;
        this.axiosClient = axios;
        this.clientLoaded();
    }

    /**
     * Run any functions that are supposed to be called once the client has loaded successfully.
     */
    clientLoaded() {
        if (this.props.hasOwnProperty("onReady")) {
            this.props.onReady(this);
        }
    }

    /**
     * Get the identity of the current user
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The user information for the current user.
     */
    async getIdentity(errorCallback) {
        try {
            const isLoggedIn = await this.authenticator.isUserLoggedIn();

            if (!isLoggedIn) {
                return undefined;
            }

            return await this.authenticator.getCurrentUserInfo();
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    async login() {
        this.authenticator.login();
    }

    async logout() {
        this.authenticator.logout();
    }

    async getTokenOrThrow(unauthenticatedErrorMessage) {
        const isLoggedIn = await this.authenticator.isUserLoggedIn();
        if (!isLoggedIn) {
            throw new Error(unauthenticatedErrorMessage);
        }

        return await this.authenticator.getUserToken();
    }

    async getTokenOrThrow(unauthenticatedErrorMessage) {
        const isLoggedIn = await this.authenticator.isUserLoggedIn();
        if (!isLoggedIn) {
            throw new Error(unauthenticatedErrorMessage);
        }

        return await this.authenticator.getUserToken();
    }

    /**
     * Gets the Goal for the given ID.
     * @param goalId Unique identifier for a playlist
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The goal's metadata.
     */
    async getGoal(goalId, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can view goals.");
            const response = await this.axiosClient.get(`goals/${goalId}`, {
                headers: {
                    Authorization: `Bearer ${token}`
                },
                data: {
                    goalId: goalId
                }
            });
            return response.data.goal;
        } catch (error) {
            this.handleError(error, errorCallback);
        }
    }

    
    /**
     * Create a new goal owned by the current user.
     * @param name The name of the goal to create.
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The playlist that has been created.
     */
    async createGoal(name, category, goalAmount, description, priority, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can create goals.");
            const response = await this.axiosClient.post(`goals`, {
                name: name,
                category: category,
                goalAmount: goalAmount,
                description: description,
                priority: priority,
            }, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data.goal;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    async updateGoalAmount(goalId, amount, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can update goals.");
            const response = await this.axiosClient.put(`goals/updateAmount/${goalId}`, {
                goalId: goalId,
                amount: amount
            }, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data.goal;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    async updateGoalDescription(goalId, description, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can update goals.");
            const response = await this.axiosClient.put(`goals/updateDescription/${goalId}`, {
                goalId: goalId,
                description: description
            }, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data.goal;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    async updateGoalPriority(goalId, priority, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can update goals.");
            const response = await this.axiosClient.put(`goals/updatePriority/${goalId}`, {
                goalId: goalId,
                priority: priority
            }, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data.goal;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    
    async deleteGoal(goalId, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can update goals.");
            const response = await this.axiosClient.delete(`goals/deleteGoal/${goalId}`, {
                headers: {
                    Authorization: `Bearer ${token}`,
                    'Content-Type': 'application/json'
                },
                data: {
                    goalId: goalId
                }
            });
            return response.data.goal;
        } catch (error) {
            this.handleError(error, errorCallback);
        }
    }



    /**
    get all order data for order table
    */

    async viewAllGoals() {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can view goals.");
            const response = await this.axiosClient.get(`/goals/`, {
                headers: {
                    Authorization: `Bearer ${token}`,
                    'Content-Type': 'application/json'
                }
            });
            const goals = response.data.goals.map(goal => {
                const { id: goalId, name, category, goalAmount, description, priority } = goal;
                return {
                    goalId,
                    name,
                    category,
                    goalAmount,
                    description,
                    priority
                };
            });
            return goals;
        } catch (error) {
            this.handleError(error, errorCallback);
        }
    }





    // /**
    //  * Get the songs on a given playlist by the playlist's identifier.
    //  * @param id Unique identifier for a playlist
    //  * @param errorCallback (Optional) A function to execute if the call fails.
    //  * @returns The list of songs on a playlist.
    //  */
    // async getPlaylistSongs(id, errorCallback) {
    //     try {
    //         const response = await this.axiosClient.get(`playlists/${id}/songs`);
    //         return response.data.songList;
    //     } catch (error) {
    //         this.handleError(error, errorCallback)
    //     }
    // }

    // /**
    //  * Create a new playlist owned by the current user.
    //  * @param name The name of the playlist to create.
    //  * @param tags Metadata tags to associate with a playlist.
    //  * @param errorCallback (Optional) A function to execute if the call fails.
    //  * @returns The playlist that has been created.
    //  */
    // async createPlaylist(name, tags, errorCallback) {
    //     try {
    //         const token = await this.getTokenOrThrow("Only authenticated users can create playlists.");
    //         const response = await this.axiosClient.post(`playlists`, {
    //             name: name,
    //             tags: tags
    //         }, {
    //             headers: {
    //                 Authorization: `Bearer ${token}`
    //             }
    //         });
    //         return response.data.playlist;
    //     } catch (error) {
    //         this.handleError(error, errorCallback)
    //     }
    // }

    // /**
    //  * Add a song to a playlist.
    //  * @param id The id of the playlist to add a song to.
    //  * @param asin The asin that uniquely identifies the album.
    //  * @param trackNumber The track number of the song on the album.
    //  * @returns The list of songs on a playlist.
    //  */
    // async addSongToPlaylist(id, asin, trackNumber, errorCallback) {
    //     try {
    //         const token = await this.getTokenOrThrow("Only authenticated users can add a song to a playlist.");
    //         const response = await this.axiosClient.post(`playlists/${id}/songs`, {
    //             id: id,
    //             asin: asin,
    //             trackNumber: trackNumber
    //         }, {
    //             headers: {
    //                 Authorization: `Bearer ${token}`
    //             }
    //         });
    //         return response.data.songList;
    //     } catch (error) {
    //         this.handleError(error, errorCallback)
    //     }
    // }

    // /**
    //  * Search for a soong.
    //  * @param criteria A string containing search criteria to pass to the API.
    //  * @returns The playlists that match the search criteria.
    //  */
    // async search(criteria, errorCallback) {
    //     try {
    //         const queryParams = new URLSearchParams({ q: criteria })
    //         const queryString = queryParams.toString();

    //         const response = await this.axiosClient.get(`playlists/search?${queryString}`);

    //         return response.data.playlists;
    //     } catch (error) {
    //         this.handleError(error, errorCallback)
    //     }

    // }


    /**
     * Helper method to log the error and run any error functions.
     * @param error The error received from the server.
     * @param errorCallback (Optional) A function to execute if the call fails.
     */
    handleError(error, errorCallback) {
        console.error(error);

        const errorFromApi = error?.response?.data?.error_message;
        if (errorFromApi) {
            console.error(errorFromApi)
            error.message = errorFromApi;
        }

        if (errorCallback) {
            errorCallback(error);
        }
    }
}
