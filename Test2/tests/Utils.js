
exports.Open_URL = function Open_URL(browser, URL)
{
  browser
  .url(URL)
  
  browser
  .windowMaximize()

  .pause(1000)
}


exports.case_insensitive_search = function case_insensitive_search(str,search_str)
  {
    //var result= str.search(new RegExp(search_str, "A"))
    let result=str.equalsIgnoreCase(search_str) // true
    return result
    /* if (result==true)
    return 'Matched'
    else
    return 'Not Matched'  */
   }
