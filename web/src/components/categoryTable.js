import BindingClass from '../util/bindingClass';

export default class CategoryTable extends BindingClass {
    constructor() {
        super();

        const methodsToBind = ['addTableToPage', 'buildTable'];
        this.bindClassMethods(methodsToBind, this);
    }

    addTableToPage(requestedCategory, data) {
        console.log('CategoryTable.js building...');

        const table = this.buildTable(data, requestedCategory);
        const container = document.getElementById('category-table-container');
        table.classList.add('table-container'); // Add a class to style the table
        container.appendChild(table);
    }

    buildTable(data, requestedCategory) {
        if (!Array.isArray(data)) {
            console.error('Error: data is not an array!');
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

        // Create the order table body rows and only show unprocessed requests
        data
            .filter((item) => item.category === requestedCategory)
            .forEach((item) => {
                const row = table.insertRow();
                row.classList.add('goal-row');
                const cells = [
                    item.goalId,
                    item.name,
                    item.category,
                    item.goalAmount,
                    item.description,
                    item.priority,
                    item.completionStatus,
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
