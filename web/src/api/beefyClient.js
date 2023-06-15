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

        const methodsToBind = ['clientLoaded', 'getIdentity', 'login', 'getTokenOrThrow', 'logout', 'getGoal', 'getAllGoalData', 'createGoal', 'deleteGoal', 'updateGoalAmount', 'updateGoalDescription', 'updateGoalPriority'];
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


    async getAllGoalData() {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can update goals.");
            const response = await this.axiosClient.get('/goals/getAllGoals/', {
                headers: {
                    Authorization: `Bearer ${token}`,
                    'Content-Type': 'application/json',
                },
            });

            console.log('Response:', response); // Check the response received

            if (!response.data || !Array.isArray(response.data.goalModel)) {
                console.error('Error: Invalid goal data');
                return [];
            }

            const goalModel = response.data.goalModel;
            const goals = goalModel.map((goal) => {
                const {
                    goalId,
                    name,
                    category,
                    goalAmount,
                    description,
                    priority,
                    completionStatus,
                    userId,
                } = goal;

                return {
                    goalId,
                    name,
                    category,
                    goalAmount,
                    description,
                    priority,
                    completionStatus,
                    userId,
                };
            });

            console.log('Goals:', goals); // Check if goals are correctly extracted

            return goals;
        } catch (error) {
            console.error('Error: Unable to get goal data:', error);
            return [];
        }
    }



}
