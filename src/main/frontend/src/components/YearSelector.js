import React, { useState } from 'react';

function YearSelector(props) {
  const [year, setYear] = useState('');

  const handleChange = (event) => {
    setYear(event.target.value);
    if (props.onChange) {
      props.onChange(event.target.value);
    }
  };

  return (
    <div>
      <select value={year} onChange={handleChange}>
        <option value="">Select a Year</option>
        <option value="1940">1940's</option>
        <option value="1950">1950's</option>
        <option value="1960">1960's</option>
        <option value="1970">1970's</option>
		<option value="1980">1980's</option>
        <option value="1990">1990's</option>
      </select>
    </div>
  );
}

export default YearSelector;
