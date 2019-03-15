import 'jsdom-global/register';
import React from 'react';
import App from '../src/App';
import Enzyme, {mount} from "enzyme";
import {expect} from 'chai';
import Adapter from 'enzyme-adapter-react-16';
import {describe} from "mocha";

Enzyme.configure({adapter: new Adapter()});


describe('app', () => {
    it('renders without crashing', () => {
        mount(<App/>);
        expect(true).to.equal(true);
    });
});
