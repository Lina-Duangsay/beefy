import BindingClass from '../util/bindingClass';

export default class PriorityTable extends BindingClass {
    constructor() {
        super();

        const methodsToBind = ['addTableToPage', 'buildTable'];
        this.bindClassMethods(methodsToBind, this);
    }

    addTableToPage(requestedPriority, data) {
        console.log('PriorityTable.js building...');

        const table = this.buildTable(data, requestedPriority);
        const container = document.getElementById('priority-table-container');
        table.classList.add('table-container'); 
        container.appendChild(table);
    }

    buildTable(data, requestedPriority) {
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

        data
            .filter((item) => item.priority === requestedPriority)
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
