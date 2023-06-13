import BeefyClient from '../api/beefyClient';
import BindingClass from '../util/bindingClass';

export default class Table extends BindingClass {
    constructor() {
        super();

        const methodsToBind = ['fetchData', 'displayTable'];
        this.bindClassMethods(methodsToBind, this);

        this.client = new BeefyClient();
        this.data = [];
    }

    async fetchData() {
        try {
            const response = await this.client.fetchData(); // Replace with your method to fetch data from DynamoDB

            this.data = response.data.Items; // Assuming the response contains an array of items
            this.displayTable();
        } catch (error) {
            console.error('Error fetching data:', error);
        }
    }

    displayTable() {
        const table = document.createElement('table');
        table.classList.add('table-container'); // Add a class to style the table

        // Create the table header row
        const headerRow = document.createElement('tr');
        const headers = ['Goal ID', 'Name', 'Amount', 'Category', 'Priority', 'Completion Status', 'Description'];
        headers.forEach(header => {
            const th = document.createElement('th');
            th.innerText = header;
            headerRow.appendChild(th);
        });
        table.appendChild(headerRow);

        // Create the table body rows
        this.data.forEach(item => {
            const row = document.createElement('tr');
            row.classList.add('goal-row'); // Add a class to style the row

            headers.forEach(header => {
                const cell = document.createElement('td');
                cell.innerText = item[header] || ''; // Assuming the item object has properties matching the headers
                row.appendChild(cell);
            });

            table.appendChild(row);
        });

        document.body.appendChild(table);
    }
}
