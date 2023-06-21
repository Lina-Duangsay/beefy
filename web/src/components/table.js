import BeefyClient from '../api/beefyClient';
import BindingClass from '../util/bindingClass';

export default class Table extends BindingClass {
    constructor() {
        super();

        const methodsToBind = ['addTableToPage', 'buildTable'];
        this.bindClassMethods(methodsToBind, this);

        this.client = new BeefyClient();
    }

    async addTableToPage() {
        console.log('table.js building...');
        const token = await this.client.getTokenOrThrow("Only authenticated users can update goals.");
        const data = await this.client.getAllGoalData(token);
        const table = this.buildTable(data);
        const container = document.getElementById('allgoal-table-container');
        table.classList.add('table-container'); 
        container.appendChild(table);
    }

    buildTable(data) {
        if (!Array.isArray(data)) {
            console.error('Error: data is not an array!');
            return;
        }

        const table = document.createElement('table');
        table.classList.add('table-container');


        const headerRow = table.insertRow();
        const headers = ['Goal Id', 'Name', 'Category', 'Amount', 'Description', 'Priority', 'Status'];
        headers.forEach((header) => {
            const th = document.createElement('th');
            th.innerText = header;
            headerRow.appendChild(th);
        });


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
                td.innerText = cell;
                row.appendChild(td);
            });
        });

        return table;
    }

}
