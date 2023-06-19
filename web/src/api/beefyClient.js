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

        const methodsToBind = ['clientLoaded', 'getIdentity', 'login', 'getTokenOrThrow', 'logout', 'getGoal', 
        'getAllGoalData', 'createGoal', 'deleteGoal', 'updateGoalAmount', 'updateGoalDescription', 'updateGoalStatus', 'updateGoalPriority',
        'getGoalByCategory', 'getGoalByName'];
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
     * @param goalId Unique identifier for a category
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
 * This function retrieves goals by category for authenticated users using an axios client.
 * @param category - The category parameter is a string that represents the category of goals to
 * retrieve from the server.
 * @returns an array of goals that belong to a specific category. If there is an error, it returns an
 * empty array.
 */
    async getGoalByCategory(category) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can update goals.");
            const response = await this.axiosClient.get(`/goals/category/${category}`, {
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

            console.log('Goals:', goals); 

            return goals;
        } catch (error) {
            console.error('Error: Unable to get goal data:', error);
            return [];
        }
    }


   /**
    * This function retrieves a goal by its name from an API and returns an array of goals.
    * @param goalName - The name of the goal that needs to be retrieved.
    * @returns an array of goal objects. If there is an error, it returns an empty array.
    */
    async getGoalByName(goalName) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can retrieve goals.");
            const response = await this.axiosClient.get(`/goals/name/${goalName}`, {
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

            console.log('Goals:', goals); 

            return goals;
        } catch (error) {
            console.error('Error: Unable to get goal data:', error);
            return [];
        }
    }

    /**
* Gets the Goal for the given ID.
* @param name identifier for a goal
* @param errorCallback (Optional) A function to execute if the call fails.
* @returns The goal's metadata.
*/
    async getGoalById(goalId) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can retrieve goals.");
            const response = await this.axiosClient.get(`/goals/${goalId}`, {
                headers: {
                    Authorization: `Bearer ${token}`,
                    'Content-Type': 'application/json',
                },
            });

            console.log('Response:', response); // Check the response received

            if (!response.data || typeof response.data.goalModel !== 'object') {
                console.error('Error: Invalid goal data');
                return [];
            }

            const goalModel = response.data.goalModel;

            const goal = {
                goalId: goalModel.goalId,
                name: goalModel.name,
                category: goalModel.category,
                goalAmount: goalModel.goalAmount,
                description: goalModel.description,
                priority: goalModel.priority,
                completionStatus: goalModel.completionStatus,
                userId: goalModel.userId,
            };

            console.log('Goal:', goal);

            return [goal]; 
        } catch (error) {
            console.error('Error: Unable to get goal data:', error);
            return [];
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

    /**
     * This is an asynchronous function that updates the amount of a goal with a given ID, using an
     * authentication token and error handling.
     * @param goalId - The ID of the goal that needs to be updated.
     * @param amount - The amount parameter is the new target amount for the goal that needs to be
     * updated. It is the value that will replace the current target amount of the goal.
     * @param errorCallback - The errorCallback parameter is a function that will be called if an error
     * occurs during the execution of the updateGoalAmount function. It allows the caller of the
     * function to handle errors in a custom way, such as displaying an error message to the user or
     * logging the error for debugging purposes. The function should take
     * @returns the `goal` object from the response data of the PUT request made to the
     * `goals/updateAmount/` endpoint.
     */
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

    /**
     * This is an asynchronous function that updates the completion status of a goal with a given ID
     * and returns the updated goal data, while handling errors with a provided callback function.
     * @param goalId - The ID of the goal that needs to be updated.
     * @param errorCallback - The errorCallback parameter is a function that will be called if an error
     * occurs during the execution of the updateGoalStatus function. It is used to handle errors and
     * display error messages to the user.
     * @returns the `goal` data from the response object.
     */
    async updateGoalStatus(goalId, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can update goals.");
            const response = await this.axiosClient.put(`goals/updateStatus/${goalId}`, {
                goalId: goalId,
                completionStatus: true
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

    /**
     * This is an asynchronous function that updates the description of a goal with a given ID, using
     * an axios client and a token for authentication, and returns the updated goal data.
     * @param goalId - The ID of the goal that needs to be updated.
     * @param description - The new description for the goal that needs to be updated.
     * @param errorCallback - The errorCallback parameter is a function that will be called if an error
     * occurs during the execution of the updateGoalDescription function. It allows the caller of the
     * function to handle errors in a custom way, such as displaying an error message to the user or
     * logging the error.
     * @returns the updated goal object (specifically, the `goal` property of the `response.data`
     * object) after updating its description.
     */
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

    /**
     * This is an asynchronous function that updates the priority of a goal with a given ID, using an
     * authentication token and error handling.
     * @param goalId - The ID of the goal that needs to be updated.
     * @param priority - The priority parameter is the new priority value that you want to set for the
     * goal with the specified goalId.
     * @param errorCallback - The errorCallback parameter is a function that will be called if an error
     * occurs during the execution of the updateGoalPriority function. It allows the caller of the
     * function to handle errors in a custom way, such as displaying an error message to the user or
     * logging the error for debugging purposes. The function should take
     * @returns the updated goal object from the server response data.
     */
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

    
    /**
     * This is an asynchronous function that deletes a goal using an axios client and returns the
     * deleted goal data.
     * @param goalId - The ID of the goal that needs to be deleted.
     * @param errorCallback - The errorCallback parameter is a function that will be called if an error
     * occurs during the execution of the deleteGoal method. It allows the caller of the method to
     * handle errors in a custom way, such as displaying an error message to the user or logging the
     * error for debugging purposes. The function should take one
     * @returns the `goal` data from the response of the `DELETE` request made to the
     * `goals/deleteGoal/` endpoint, after passing the `Authorization` token and
     * `Content-Type` headers and the `goalId` data in the request body. If an error occurs, the
     * function will handle it by calling the `handleError` function and passing the error and the
     */
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
     * This function retrieves all goal data from the server and returns it in a formatted array.
     * @returns an array of goal objects. If there is an error, it returns an empty array.
     */
    async getAllGoalData() {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can update goals.");
            const response = await this.axiosClient.get('/goals/getAllGoals/', {
                headers: {
                    Authorization: `Bearer ${token}`,
                    'Content-Type': 'application/json',
                },
            });

            console.log('Response:', response); 

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

            console.log('Goals:', goals);

            return goals;
        } catch (error) {
            console.error('Error: Unable to get goal data:', error);
            return [];
        }
    }



}
