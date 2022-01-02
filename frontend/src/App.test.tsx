import React, { useState as useStateMock } from 'react';
import { shallow } from 'enzyme';

import Container from './components/Container';
import { CountdownCircleTimer } from 'react-countdown-circle-timer';
import Table from './components/Table';
import App from './App'
import { IServicesData } from './fixtures/types';

jest.mock('react', () => ({
  // @ts-ignore Spread types may only be created from object types.
  ...jest.requireActual('react'),
  useState: jest.fn(),
}));

describe('App component', () => {
  describe('expect table to be rendered', () => {
    const setRefresh = jest.fn();
    (useStateMock as jest.Mock<any>).mockReturnValueOnce([
      false,
      setRefresh,
    ]);
    const wrapper = shallow(<App />);
    it('renders app', () => {
      expect(wrapper.find(".App")).toHaveLength(1);
      expect(wrapper.find(".App").children()).toHaveLength(4);
    });
    describe('main poller', () => {
      it('header', () => {
        expect(wrapper.find('h2').text()).toContain('Livi/Kry Service poller');
      })
      it('Container', () => {
        const createService = jest.fn();
        const deleteService = jest.fn();
        const updateService = jest.fn();
        const servicesData: IServicesData[] = [];
        const childrenProps = {
          createService, updateService, deleteService, servicesData
        }
        const containerWrapper = wrapper.find(Container)
        expect(containerWrapper.props()).toHaveProperty('refresh', false);
        const containerChildren = containerWrapper.renderProp('children')(childrenProps);
        expect(containerChildren.find(Table)).toHaveLength(1);
      });
    });
    describe('Countdown timer', () => {
      it('header', () => {
        expect(wrapper.find('h3').text()).toContain('Time until service refresh: ');
      });
      it('CountdownCircleTimer', () => {
        const countdownTimer = wrapper.find(CountdownCircleTimer);
        expect(countdownTimer).toHaveLength(1);
        expect(countdownTimer.props()).toMatchObject({
          isPlaying: true,
          duration: 60,
          colors: [
            ['#004777', 0.33],
            ['#F7B801', 0.33],
            ['#A30000', 0.33],
          ],
        });
        // @ts-ignore 
        countdownTimer.prop('onComplete')()
        expect(setRefresh).toHaveBeenCalled();
        const countdownChildren = countdownTimer.renderProp('children')({ remainingTime: 10 });
        expect(countdownChildren.text()).toContain('10');
      });
    });
  });
});