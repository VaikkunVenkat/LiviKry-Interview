
import Container from "./components/Container";
import React, { useState } from "react";
import { CountdownCircleTimer } from "react-countdown-circle-timer";
import "./App.css";
import Table from "./components/Table";
import { DELAY_TIME } from "./utils";

const App: React.FC<any> = () => {
  const [refresh, setRefresh] = useState<boolean>(false)
  return (
    <div className="App">
      <h2>Livi/Kry Service poller</h2>
      <Container refresh={refresh}>
        {(props) => (
          <Table {...props} />
        )}
      </Container>
      <h3>Time until service refresh: </h3>
      <CountdownCircleTimer
        onComplete={() => {
          setRefresh(prevState => !prevState);
          return [true, 0]
        }}
        isPlaying
        duration={DELAY_TIME}
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
