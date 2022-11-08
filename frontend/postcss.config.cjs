const nesting = require('tailwindcss/nesting');
const tailwindcss = require('tailwindcss');
const autoprefixer = require('autoprefixer');
// const cssnano = require('cssnano');

const config = {
	plugins: [
		//Some plugins, like tailwindcss/nesting, need to run before Tailwind,
		nesting(),
		tailwindcss(),
		//But others, like autoprefixer, need to run after,
		autoprefixer,
		// cssnano({
			// preset: 'default'
		// })
	]
};

module.exports = config;
