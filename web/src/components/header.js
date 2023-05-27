import BeefyClient from '../api/beefyClient';
import BindingClass from "../util/bindingClass";

/**
 * The header component for the website.
 */
export default class Header extends BindingClass {
    constructor() {
        super();

        const methodsToBind = [
            'addHeaderToPage', 'createSiteTitle', 'createUserInfoForHeader',
            'createLoginButton', 'createLoginButton', 'createLogoutButton'
        ];
        this.bindClassMethods(methodsToBind, this);

        this.client = new BeefyClient();
    }

    /**
     * Add the header to the page.
     */
    async addHeaderToPage() {
        const currentUser = await this.client.getIdentity();

        const siteTitle = this.createSiteTitle();
        const userInfo = this.createUserInfoForHeader(currentUser);

        const header = document.getElementById('header');
        header.appendChild(siteTitle);
        header.appendChild(userInfo);
    }

    createSiteTitle() {
        const homeButton = document.createElement('a');
        homeButton.classList.add('header_home');
        homeButton.href = 'index.html';

        const img = document.createElement('img');
        img.src = 'beef.png';
        img.alt = 'beefy';
        img.style.width = '1800px';
        img.style.height = '450px';
        homeButton.appendChild(img);

        const siteTitle = document.createElement('div');
        siteTitle.classList.add('site-title');
        siteTitle.appendChild(homeButton);

        return siteTitle;
    }

    createUserInfoForHeader(currentUser) {
        const userInfo = document.createElement('div');
        userInfo.classList.add('user');

        if (currentUser) {
            const dashboardButton = document.createElement('a');
            dashboardButton.classList.add('button');
            dashboardButton.href = 'employeeDashboard.html';
            dashboardButton.innerText = 'Employee Dashboard';

            const createOrderButton = document.createElement('a');
            createOrderButton.classList.add('button');
            createOrderButton.href = 'createOrder.html';
            createOrderButton.innerText = 'Create Order';

            const logoutButton = this.createLogoutButton(currentUser);

            userInfo.appendChild(dashboardButton);
            userInfo.appendChild(document.createTextNode('\u00A0')); // add a space between the buttons
            userInfo.appendChild(createOrderButton);
            userInfo.appendChild(document.createTextNode('\u00A0')); // add a space between the buttons
            userInfo.appendChild(logoutButton);

            const style = window.getComputedStyle(logoutButton);
            const leftOffset = parseInt(style.getPropertyValue('left'), 10);
            const buttonWidth = parseInt(style.getPropertyValue('width'), 10);

            createOrderButton.style.left = `${leftOffset - buttonWidth - 80}px`; // adjust the left position of the Create Order button
            dashboardButton.style.left = `${leftOffset - (2 * buttonWidth) - 160}px`; // adjust the left position of the Dashboard button
            userInfo.style.width = `${leftOffset + (2 * buttonWidth) + 160}px`; // adjust the width of the userInfo container to include the gap and buttons
        } else {
            const loginButton = this.createLoginButton();
            userInfo.appendChild(loginButton);
        }

        return userInfo;
    }

    createLoginButton() {
        return this.createButton('Login', this.client.login);
    }

    createLogoutButton(currentUser) {
        return this.createButton(`Logout: ${currentUser.name}`, this.client.logout);
    }

    createButton(text, clickHandler) {
        const button = document.createElement('a');
        button.classList.add('button');
        button.href = '#';
        button.innerText = text;

        button.addEventListener('click', async () => {
            await clickHandler();
        });

        return button;
    }
}
