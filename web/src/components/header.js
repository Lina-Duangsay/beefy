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
            'createLoginButton', 'createLogoutButton', 'createButton'
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
        img.src = 'beef_by_trin_copy.png';
        img.alt = 'beefy';
        img.style.width = '350px';
        img.style.height = '160px';
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
            dashboardButton.href = 'userDashboard.html';
            dashboardButton.innerText = 'Dashboard';

            const logoutButton = this.createLogoutButton(currentUser);

            userInfo.appendChild(dashboardButton);
            userInfo.appendChild(document.createTextNode('\u00A0')); 
            userInfo.appendChild(logoutButton);

            const style = window.getComputedStyle(logoutButton);
            const leftOffset = parseInt(style.getPropertyValue('left'), 10);
            const buttonWidth = parseInt(style.getPropertyValue('width'), 10);

            dashboardButton.style.left = `${leftOffset - (2 * buttonWidth) - 160}px`; 
            userInfo.style.width = `${leftOffset + (2 * buttonWidth) + 160}px`;
            
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
