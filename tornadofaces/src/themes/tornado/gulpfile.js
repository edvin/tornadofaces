var gulp           = require('gulp'),
    runSequence    = require('run-sequence'),
    autoprefixer   = require('gulp-autoprefixer'),
    minifyCSS      = require('gulp-minify-css'),
    sourcemaps     = require('gulp-sourcemaps'),
    replace        = require('gulp-replace'),
    sass           = require('gulp-ruby-sass');

var build = '../../main/resources/META-INF/resources/tornadofaces-tornado/';

gulp.task('sass', function() {
  return sass('scss/theme.scss', { style: 'nested', sourcemap: true, bundleExec: true} )
    .on('error', function(e) { console.log(e); })
    .pipe(autoprefixer({ browsers: ['last 2 versions', 'ie 10']}))
    //.pipe(minifyCSS({}))
    .pipe(sourcemaps.write('.', { includeContent: false, sourceRoot: 'scss/theme.scss'}))
    .pipe(replace('theme.css.map', 'theme.css.map?ln=tornadofaces'))
    .pipe(gulp.dest(build));
});

gulp.task('build', function() {
  runSequence(['sass'], function() {
    console.log("Successfully built.");
  })
});

gulp.task('watch', ['build'], function() {
  gulp.watch(['./scss/**/*'], ['sass']);
});

gulp.task('default', ['build'], function() {
});
