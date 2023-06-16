import BindingClass from '../util/bindingClass';

export default class IdTable extends BindingClass {
    constructor() {
        super();

        const methodsToBind = ['addTableToPage', 'buildTable'];
        this.bindClassMethods(methodsToBind, this);
    }

    addTableToPage(requestedGoalId, data) {
        console.log('IdTable.js building...');

        const table = this.buildTable(data, requestedGoalId);
        const container = document.getElementById('goalId-table-container');
        table.classList.add('table-container'); // Add a class to style the table
        container.appendChild(table);
    }

    buildTable(data) {
        if (!data) {
            console.error('Error: Invalid goal data');
            return;
        }

        const isArray = Array.isArray(data);
        if (!isArray && typeof data !== 'object') {
            console.error('Error: Invalid goal data');
            return;
        }

        const table = document.createElement('table');
        table.classList.add('table-container');

        // Create the table header row
        const headerRow = table.insertRow();
        const headers = ['Goal Id', 'Name', 'Category', 'Amount', 'Description', 'Priority', 'Status'];
        headers.forEach((header) => {
            const th = document.createElement('th');
            th.innerText = header;
            headerRow.appendChild(th);
        });

        if (isArray) {
            // Create table rows for each goal object in the array
            data.forEach((goal) => {
                const row = table.insertRow();
                row.classList.add('goal-row');
                const cells = [
                    goal.goalId,
                    goal.name,
                    goal.category,
                    goal.goalAmount,
                    goal.description,
                    goal.priority,
                    goal.completionStatus,
                ];
                cells.forEach((cell) => {
                    const td = document.createElement('td');
                    td.innerText = cell !== undefined ? cell.toString() : ''; // Convert undefined to empty string
                    row.appendChild(td);
                });
            });
        } else {
            // Create a single table row for the goal object
            const row = table.insertRow();
            row.classList.add('goal-row');
            const cells = [
                data.goalId,
                data.name,
                data.category,
                data.goalAmount,
                data.description,
                data.priority,
                data.completionStatus,
            ];
            cells.forEach((cell) => {
                const td = document.createElement('td');
                td.innerText = cell !== undefined ? cell.toString() : ''; // Convert undefined to empty string
                row.appendChild(td);
            });
        }

        return table;
    }



}
