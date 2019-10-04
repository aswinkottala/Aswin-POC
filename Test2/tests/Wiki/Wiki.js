//To Access the selectors defined in selectors.js
require ('../Selectors.js')

//To Access the url details from config.js
require ('../config.js')

//To Access any User defined functions from Util.js
const Utils = require ('../Utils.js')

//To Access the data sheet defined in the xls file
const xlsx = require('xlsx');
const utils = xlsx.utils;
const specification_xlsx_path = 'tests.xlsx';
let workbook = xlsx.readFile(specification_xlsx_path);
let worksheet = workbook.Sheets['TestData'];
let json = utils.sheet_to_json(worksheet);

module.exports = {
    tags: ['wiki'],
  
    'Wiki -Search' : function (browser) {
        for (let i=0; i<json.length; i++) 
        {
        console.log("No of rows "+json.length);
        let searchstr = json[i]['SearchString'] ? json[i]['SearchString'] : '';
        let language = json[i]['Language'] ? json[i]['Language'] : '';
        let translationtext = json[i]['Translation'] ? json[i]['Translation'] : '';
        lan=".//a[text()='"+language+"']"

            Utils.Open_URL(browser,wiki_url)
           
            browser
            
            .useXpath()
            //Enter a search string
            .setValue(search,searchstr)

            //Click on the Language dropdown
            .click(search_language)

            //Select English language from the dropdown
            .click(langEN)

            //Click on the search icon
            .click(search_icon)
            
            //Check the search text is displayed on the results page
            .verify.containsText(search_results_string, searchstr)
            
            //Verify if link to language is displayed
            .verify.elementPresent(lan)

            //Click on the language link 
            .click(lan)

            //Verify the search results text with the serch string is displayed in different language
            .verify.containsText(search_results_string, translationtext)

            //Verify link to English language is displayed 
            .verify.containsText(langeng, "English")
        }
    
browser.end()
}       
};