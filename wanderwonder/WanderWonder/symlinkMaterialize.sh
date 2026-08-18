# Materialize fix
rm -rf "$(pwd)"/node_modules/materialize-css/bin/picker.js;
ln -s "$(pwd)"/node_modules/pickadate/lib/picker.js "$(pwd)"/node_modules/materialize-css/bin/picker.js;
# FONTS copied from npm modules
cp -R "$(pwd)"/node_modules/font-awesome/fonts/* "$(pwd)"/public/fonts;
rm -rf "$(pwd)"/public/fonts/roboto;
cp -R "$(pwd)"/node_modules/materialize-css/fonts/roboto "$(pwd)"/public/fonts/roboto;
echo "all setup";
