import BeefyClient from "../api/beefyClient";
import BindingClass from "../util/bindingClass";

export default class Table extends BindingClass {
    constructor() {
        super();

        this.bindClassMethods(['addTableToPage', 'buildTable'], this);
        this.client = new BeefyClient();
        this.addTableToPage();
    }

    async addTableToPage() {
        console.log('Table.js building...');
        const currentUser = await this.client.getIdentity();
        const data = await this.client.getData();
        const table = this.buildTable(data);
        const container = document.getElementById('table-container');
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

        // Create the table header row
        const headerRow = table.insertRow();
        const headers = ['Goal ID', 'Name', 'Amount', 'Category', 'Priority', 'Completion Status', 'Description'];
        headers.forEach(header => {
            const th = document.createElement('th');
            th.innerText = header;
            headerRow.appendChild(th);
        });

        // Create the table body rows
        data.forEach(item => {
            const row = table.insertRow();
            row.classList.add('goal-row'); // Add a class to style the row
            const cells = [item.goalId, item.name, item.goalAmount, item.category, item.priority, item.completionStatus, item.description];
            cells.forEach(cell => {
                const td = document.createElement('td');
                td.innerText = cell;
                row.appendChild(td);
            });
        });

        return table;
    }
}
