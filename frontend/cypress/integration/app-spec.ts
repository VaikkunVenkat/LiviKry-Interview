// type definitions for Cypress object "cy"
/// <reference types="cypress" />

describe("App", () => {
  beforeEach(() => {
    cy.visit("/");
  });

  it("Add row", () => {
    cy.intercept('POST', '/api/services').as('post');
    cy.get('.MTableToolbar-actions-8 > :nth-child(1) > div > :nth-child(2) > .MuiButtonBase-root > .MuiIconButton-label > .material-icons').click();
    cy.get('.MuiTable-root .MuiTableRow-root:nth-child(6)').within(() => {
      cy.get('.MuiFormControl-root:nth(0)').type('IP Fast');
      cy.get('.MuiFormControl-root:nth(1)').type('https://ip-fast.com/api/ip/{enter}');
    });
    cy.get('table').contains('td', 'IP Fast').should('be.visible');
    cy.get('table').contains('td', 'https://ip-fast.com/api/ip/').should('be.visible');

    cy.wait('@post').then((interception) => {
      expect(interception.response?.statusCode).to.equal(201);
      expect(interception.response?.body).to.have.property('name', 'IP Fast');
      expect(interception.response?.body).to.have.property('url', 'https://ip-fast.com/api/ip/');
    });
  });

  // given more time I would complete the following end to end tests.

  /*   it('Update Row', () => {
    cy.intercept('PUT', '/api/services/1').as('put');
    cy.get('.MuiTable-root .MuiTableRow-root:nth-child(4)').within(() => {
      cy.get('button:first-child').click();
      cy.get('.MuiFormControl-root:first-child').first().clear().type('Data USA{enter}')
    })
    cy.get('table').contains('td', 'datauser').should('not.exist')

    cy.get('table').contains('td', 'Data USA').should('be.visible')
    
    cy.wait('@put').then((interception) => {
      expect(interception.response?.statusCode).to.equal(200);
      expect(interception.response?.body).to.have.property('name', 'Data USA');
    });
  }); */

  /*   it('Delete Row', () => {
    cy.intercept('DELETE', '/api/services/1').as('put');
    cy.get('.MuiTable-root .MuiTableRow-root:nth-child(4)').within(() => {
      cy.get('button:nth-child(2)').click().type('{enter}');
    })
    
    cy.wait('@delete').then((interception) => {
      expect(interception.response?.statusCode).to.equal(200);
    });
  }) */
});
