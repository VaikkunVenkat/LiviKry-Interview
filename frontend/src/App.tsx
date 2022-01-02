import React from "react";
import { CountdownCircleTimer } from "react-countdown-circle-timer";
import "./App.css";
import Table from "./components/Table";

const App: React.FC<any> = () => {
  return (
    <div className="App">
      <h1>Livi/Kry Service poller</h1>
      <Table />
      <CountdownCircleTimer
        onComplete={() => {
          // do your stuff here
          return [true, 0]
        }}
        isPlaying
        duration={10}
        colors={[
          ['#004777', 0.33],
          ['#F7B801', 0.33],
          ['#A30000', 0.33],
        ]}
      >
        {({ remainingTime }) => remainingTime}
      </CountdownCircleTimer>
    </div>
  );
};

export default App;
