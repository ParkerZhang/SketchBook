"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
const Meeting_1 = require("../sandbox/Meeting");
const AgentSandbox_1 = require("../sandbox/AgentSandbox");
describe('Stage 2 - Greetings Tests', () => {
    test('Agent joins are logged', () => {
        const meeting = new Meeting_1.Meeting('Test Meeting');
        const agent = new AgentSandbox_1.AgentSandbox('Alice');
        meeting.addAgent(agent);
        expect(meeting.log[0]).toContain('Alice joins the meeting.');
    });
    test('Agents respond when joining', () => {
        const meeting = new Meeting_1.Meeting('Test Meeting');
        const agent = new AgentSandbox_1.AgentSandbox('Bob');
        meeting.addAgent(agent);
        expect(meeting.log.some(l => l.includes('Bob:'))).toBe(true);
        expect(meeting.log.some(l => l.includes('Nice to meet you!'))).toBe(true);
    });
    test('Existing agents greet newcomer', () => {
        const meeting = new Meeting_1.Meeting('Test Meeting');
        const alice = new AgentSandbox_1.AgentSandbox('Alice');
        const bob = new AgentSandbox_1.AgentSandbox('Bob');
        meeting.addAgent(alice);
        meeting.addAgent(bob);
        expect(meeting.log.some(l => l.includes('Alice:') && l.includes('Bob'))).toBe(true);
    });
    test('resumeGreetings() logs resuming', () => {
        const meeting = new Meeting_1.Meeting('Test Meeting');
        const agent = new AgentSandbox_1.AgentSandbox('Alice');
        meeting.addAgent(agent);
        meeting.resumeGreetings();
        expect(meeting.log.some(l => l.includes('Meeting Resumed'))).toBe(true);
        expect(meeting.log.some(l => l.includes('is back!'))).toBe(true);
    });
    test('Full meeting lifecycle', () => {
        const meeting = new Meeting_1.Meeting('Lifecycle Test');
        const alice = new AgentSandbox_1.AgentSandbox('Alice');
        const bob = new AgentSandbox_1.AgentSandbox('Bob');
        meeting.addAgent(alice);
        meeting.addAgent(bob);
        expect(meeting.getAgentCount()).toBe(2);
        expect(meeting.log.length).toBeGreaterThan(0);
    });
});
//# sourceMappingURL=stage2.greetings.test.js.map