// ***********************************************************
// This example support/index.js is processed and
// loaded automatically before your test files.
//
// This is a great place to put global configuration and
// behavior that modifies Cypress.
//
// You can change the location of this file or turn off
// automatically serving support files with the
// 'supportFile' configuration option.
//
// You can read more here:
// https://on.cypress.io/configuration
// ***********************************************************

// Import commands.js using ES2015 syntax:
import './commands'

// Alternatively you can use CommonJS syntax:
// require('./commands')

before(function () {
    cy.fixture('vars.json').then((vars) => {
        cy.register(vars.rootUser, vars.defaultPass, true);
        cy.register(vars.defaultUser, vars.defaultPass);
    })
});

beforeEach(function () {
    // first call to npm fails, looks like this may be the bug: https://github.com/cypress-io/cypress/issues/6081
    cy.exec('npm version', {failOnNonZeroExit: false})
    cy.exec('npm run backend:resetDb')

    cy.fixture('vars.json').then((vars) => {
        cy.login(vars.defaultUser, vars.defaultPass);
    });
});
