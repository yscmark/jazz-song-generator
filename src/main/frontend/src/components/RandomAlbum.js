import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Spotify } from 'react-spotify-embed';

const RandomAlbum = ({ year }) => {
  const [randomAlbum, setRandomAlbum] = useState('');

  useEffect(() => {
    const fetchRandomAlbum = async () => {
      let url = '/api/songs/random';
      if (year) {
        url += `?year=${year}`; 
      }

      try {
        const response = await axios.get(url);
        setRandomAlbum(response.data); 
      } catch (error) {
        console.error('Error fetching random album:', error);
      }
    };

    fetchRandomAlbum(); 
  }, [year]); 

  return (
    <div className="center">
      {randomAlbum && <Spotify link={randomAlbum} />}
    </div>
  );
};

export default RandomAlbum;
