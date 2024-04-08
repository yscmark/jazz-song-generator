import React, { useState } from 'react';
import './App.css';
import RandomAlbum from './components/RandomAlbum';
import YearSelector from './components/YearSelector';

const App = () => {
  const [selectedYear, setSelectedYear] = useState('');

  const handleYearChange = (year) => {
    setSelectedYear(year);
  };

  return (
    <div className="app">
		<div className="content">
			<YearSelector onChange={handleYearChange} />
			<RandomAlbum year={selectedYear} />
		</div>
    </div>
  );
};

export default App;
